package erkanYerdurmaz.com.toDoListProject.converter;

import erkanYerdurmaz.com.toDoListProject.dto.BaseDto;
import erkanYerdurmaz.com.toDoListProject.entity.IEntity;

public interface EntityConverter<T extends BaseDto, U extends IEntity> {
    T to(U u);

    U to(T t);
}
