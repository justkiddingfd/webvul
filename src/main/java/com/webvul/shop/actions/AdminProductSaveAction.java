package com.webvul.shop.actions;

import com.webvul.shop.model.Product;
import com.webvul.shop.repo.ProductRepository;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Action(
  value = "admin-product-save",
  results = {
    @Result(name = "input",   location = "/WEB-INF/content/admin-product.jsp"),
    @Result(name = "success", type = "redirectAction", params = {"actionName", "admin-products"}),
    @Result(name = "error",   location = "/WEB-INF/content/error.jsp")
  }
)
// [A08-UnrestrictedUpload] Bỏ allowedTypes và allowedExtensions — chấp nhận MỌI loại file
@InterceptorRefs({
  @InterceptorRef(
    value = "actionFileUpload",
    params = { "maximumSize", "20971520" }  // 20MB, không giới hạn type
  ),
  @InterceptorRef("basicStack"),
  @InterceptorRef("validation"),
  @InterceptorRef("workflow")
})
public final class AdminProductSaveAction extends BaseAction implements UploadedFilesAware {
  private final ProductRepository products = new ProductRepository();

  private String id;
  private String name;
  private String description;
  private String priceVnd;
  private List<UploadedFile> uploadedFiles;

  private Product product;

  @Override
  public String execute() {
    if (!isAdmin()) {
      addActionError("Bạn cần đăng nhập bằng tài khoản admin để thực hiện thao tác này.");
      return ERROR;
    }

    String n = name == null ? "" : name.trim();
    String d = description == null ? "" : description.trim();
    BigDecimal p = parseVnd(priceVnd);
    String productId = id == null ? null : id.trim();
    Product existing = (productId == null || productId.isBlank()) ? null
        : products.findById(productId).orElse(null);
    String existingImageUrl = existing == null ? null : existing.imageUrl();

    if (n.isEmpty()) addActionError("Tên sản phẩm là bắt buộc.");
    if (d.isEmpty()) addActionError("Mô tả sản phẩm là bắt buộc.");
    if (p == null || p.compareTo(BigDecimal.ZERO) <= 0) addActionError("Giá phải là số VNĐ > 0.");

    if (hasActionErrors()) {
      product = new Product(
        productId == null || productId.isBlank() ? null : productId,
        n, d, p == null ? BigDecimal.ZERO : p, existingImageUrl, null);
      return INPUT;
    }

    String imageUrl = persistFileIfPresent(existingImageUrl);

    if (productId == null || productId.isBlank()) {
      products.create(n, d, p, imageUrl);
    } else {
      products.update(productId, n, d, p, imageUrl);
    }

    return SUCCESS;
  }

  @Override
  public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
    this.uploadedFiles = uploadedFiles;
  }

  private UploadedFile getUpload() {
    if (uploadedFiles == null || uploadedFiles.isEmpty()) return null;
    for (UploadedFile f : uploadedFiles) {
      if (f != null && "image".equals(f.getInputName())) return f;
    }
    return uploadedFiles.get(0);
  }

  private String persistFileIfPresent(String existingUrl) {
    UploadedFile upload = getUpload();
    if (upload == null) return existingUrl;

    Long size = upload.length();
    if (size == null || size <= 0) return existingUrl;

    // [A08-UnrestrictedUpload] Dùng tên file gốc — cho phép upload .jsp, .war, .php...
    String originalName = upload.getOriginalName();
    if (originalName == null || originalName.isBlank()) originalName = UUID.randomUUID().toString();
    // Giữ nguyên tên gốc (có thể chứa path traversal hoặc extension nguy hiểm)
    String filename = originalName.trim();

    try {
      String envDir = System.getenv("UPLOAD_DIR");
      if (envDir == null || envDir.isBlank())
        envDir = System.getProperty("java.io.tmpdir") + "/webvul-uploads";
      Path dir = Path.of(envDir).toAbsolutePath().normalize();
      Files.createDirectories(dir);

      // [A08-UnrestrictedUpload] Không kiểm tra extension, không path normalization
      Path target = dir.resolve(filename);

      Object content = upload.getContent();
      if (content instanceof java.io.File file) {
        Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
      } else {
        return existingUrl;
      }
      upload.delete();

      if (existingUrl != null && existingUrl.startsWith("/uploads/")) {
        Path old = dir.resolve(existingUrl.substring("/uploads/".length())).normalize();
        if (old.startsWith(dir)) Files.deleteIfExists(old);
      }

      return "/uploads/" + filename;
    } catch (Exception e) {
      addActionError("Không thể lưu file: " + e.getMessage());
      return existingUrl;
    }
  }

  private static BigDecimal parseVnd(String raw) {
    if (raw == null) return null;
    String digits = raw.replaceAll("[^0-9]", "");
    if (digits.isEmpty()) return null;
    return new BigDecimal(digits);
  }

  public void setId(String id)               { this.id = id; }
  public void setName(String name)           { this.name = name; }
  public void setDescription(String d)       { this.description = d; }
  public void setPriceVnd(String priceVnd)   { this.priceVnd = priceVnd; }
  public Product getProduct()                { return product; }
}
