package uk.ac.nott.cs.comp2013.mentorapp.model;

import java.util.List;
import java.util.Optional;

/**
 * Interface for any data repository. Remember that any modifications to this interface will also
 * need to be reflected in any concrete implementations.
 *
 * @param <T>  type of entities in the repository
 * @param <Id> type used to uniquely identify entities
 */
public interface Repository<T, Id> {

    /**
     * Obtain a list of all entities in the repository.
     *
     * @return the list which may be empty
     */
    List<T> selectAll();

    /**
     * Obtain the entity with a specified id, if one exists.
     *
     * @param id id to search for
     * @return the entity if it exists
     */
    Optional<T> selectById(Id id);

    /**
     * Add a new entity to the database. If an entity with that id already exists the insert will
     * fail.
     *
     * @param entity entity to add
     * @param <S>    specific subtype of the entity, to ensure the return value has the same type as
     *               the input value
     * @return the entity if it was successfully added, or {@code null} if not
     */
    <S extends T> S insert(S entity);

    /**
     * Update an existing entity in the database. If no entity with that id already exists the update
     * will fail.
     *
     * @param entity entity to update
     * @param <S>    specific subtype of the entity, to ensure the return value has the same type as
     *               the input value
     * @return the entity if it was successfully updated, or {@code null} if not
     */
    <S extends T> S update(S entity);


    /**
     * Remove an entity from the repository if one exists with the given id.
     *
     * @param id id to delete
     */
    void delete(Id id);

}
