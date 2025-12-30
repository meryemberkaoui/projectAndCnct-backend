package com.devPath.project.mapper;

import com.devPath.project.model.Project;
import com.devPath.project.model.dto.ProjectRequest;
import com.devPath.project.model.dto.ProjectResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    //Convert ProjectRequest to Project (Entity)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Project toEntity(ProjectRequest request);

    //Convert Project (Entity) to ProjectResponse
    ProjectResponse toResponse(Project project);
}
