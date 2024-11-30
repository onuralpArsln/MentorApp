package uk.ac.nott.cs.comp2013.mentorapp.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Administrator;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

/**
 * Factory class for creating various {@link Repository} implementations from different data
 * sources.
 */
public class RepositoryFactory {

  /**
   * Generate a repository using data provided in a CSV file.
   *
   * @param filename name of CSV file to read
   * @return the repository
   * @throws IOException if there was an error reading the file
   */
  public Repository<User, String> userHashMapRepository(String filename) throws IOException {
    Repository<User, String> repo = new HashMapRepository<>();

    try (InputStream data = RepositoryFactory.class.getResourceAsStream(filename)) {
      if (data == null) {
        throw new IOException(String.format("Failed to open csv file: %s", filename));
      }

      try (InputStreamReader reader = new InputStreamReader(data)) {
        Iterable<CSVRecord> rows =
            CSVFormat.EXCEL.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);

        for (CSVRecord row : rows) {
          User user = userFromCsvRecord(row);
          if (user != null) {
            repo.insert(user);
          }
        }
      }
    }

    return repo;
  }

  private User userFromCsvRecord(CSVRecord record) {
    return switch (record.get("role")) {
      case "MENTEE" ->
          new Mentee(record.get("username"), record.get("password"), record.get("cvText"));
      case "MENTOR" -> {
        Mentor m = new Mentor(record.get("username"), record.get("password"));
        if (record.get("startAvailability").isBlank() || record.get("endAvailability").isBlank()) {
          yield m;
        }

        Instant start = Instant.parse(record.get("startAvailability"));
        Instant end = Instant.parse(record.get("endAvailability"));
        m.setStartAvailability(LocalDateTime.ofInstant(start, ZoneId.systemDefault()));
        m.setEndAvailability(LocalDateTime.ofInstant(end, ZoneId.systemDefault()));
        yield m;
      }
      case "ADMIN" -> new Administrator(record.get("username"), record.get("password"));
      default -> null;
    };
  }
}

