package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.BarCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarcodeRepository  extends JpaRepository<BarCode,Long> {

    @Query("SELECT b.imageData FROM BarCode b")
    List<byte[]> findAllBarcodeImages();
}
