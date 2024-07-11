package OnlineEBookStore.demo.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ExcelDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExcelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> executeQuery(String query) {
        return jdbcTemplate.queryForList(query);
    }
}