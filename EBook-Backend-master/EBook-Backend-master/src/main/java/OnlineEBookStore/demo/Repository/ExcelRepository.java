package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.ExcelUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<ExcelUpload,Long> {

}
