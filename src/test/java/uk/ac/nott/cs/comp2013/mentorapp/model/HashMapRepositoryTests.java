package uk.ac.nott.cs.comp2013.mentorapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class HashMapRepositoryTests {

  private HashMapRepository<Blob, Integer> data;

  @BeforeEach
  public void setUp() {
    data = new HashMapRepository<>();
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3})
  public void testSelectAll(int n) {
    List<Blob> blobs = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      Blob b = new Blob(i, i);
      blobs.add(b);
      data.insert(b);
    }

    List<Blob> blobs1 = data.selectAll();
    assertEquals(n, blobs1.size(), "selectAll returned incorrect number of elements");
    for (Blob b : blobs) {
      assertTrue(blobs1.contains(b), String.format("Blob with id %d missing", b.id));
    }
  }

  @Test
  public void testSelectId_Missing() {
    assertTrue(data.selectById(2).isEmpty());
  }

  @Test
  public void testSelectId_Present() {
    Blob b = new Blob(1, 1);
    data.insert(b);

    Optional<Blob> b1 = data.selectById(1);
    assertTrue(b1.isPresent());
    assertEquals(b, b1.get());
  }

  @Test
  public void testInsert_Fail() {
    // arrange
    data.insert(new Blob(1, 1));
    // act
    Blob b = data.insert(new Blob(1, 1));
    // assert
    assertNull(b);
  }

  @Test
  public void testInsert_Succeed() {
    // act
    Blob b = data.insert(new Blob(1, 1));
    // assert
    assertEquals(new Blob(1, 1), b);
  }

  @Test
  public void testUpdate_Fail() {
    Blob b = data.update(new Blob(1, 1));
    assertNull(b);
  }

  @Test
  public void testUpdate_Succeed() {
    data.insert(new Blob(1, 1));
    Blob b = data.update(new Blob(1, 2));
    assertEquals(1, b.getId());
    assertEquals(2, b.getValue());
  }

  @Test
  public void testDelete() {
    data.insert(new Blob(1, 1));
    data.delete(1);
    assertTrue(data.selectById(1).isEmpty());
  }


  private static class Blob implements HasId<Integer> {

    private int id, value;

    public Blob(int id, int value) {
      this.id = id;
      this.value = value;
    }

    @Override
    public Integer getId() {
      return id;
    }

    @Override
    public void setId(Integer id) {
      this.id = id;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Blob b) {
        return id == b.id;
      }

      return false;
    }

    @Override
    public int hashCode() {
      return id;
    }
  }
}
