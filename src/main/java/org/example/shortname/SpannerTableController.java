package org.example.shortname;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;

@Controller
public class SpannerTableController {

    @GetMapping(value = "/timestamp", produces = "text/plain")
    public @ResponseBody String getTimeStamp() throws SQLException {
        String projectId = "your-project-id";
        String instanceId = "spring-demo";
        String databaseId = "spring-demo";

        try (Connection connection =
                     DriverManager.getConnection(
                             String.format(
                                     "jdbc:cloudspanner:/projects/%s/instances/%s/databases/%s",
                                     projectId, instanceId, databaseId))) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT CURRENT_TIMESTAMP()")) {
                    if (rs.next()) {
                        return "Connected to Cloud Spanner at" + rs.getTimestamp(1).toString();
                    }
                }
            }
        }

        return "No results from query";
    }
}
