package com.devPath.repositories;

import com.devPath.project.model.Project;
import com.devPath.project.repository.ProjectRepository;
import com.devPath.project.resources.Difficulty;
import com.devPath.project.resources.Skill;
import com.devPath.user.model.User;
import com.devPath.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser1;
    private User testUser2;
    private Project project1;
    private Project project2;
    private Project project3;

    @BeforeEach
    void setUp() {
        // Nettoyer la base de données avant chaque test
        projectRepository.deleteAll();
        userRepository.deleteAll();

        // Créer et sauvegarder les utilisateurs avec UUID
        testUser1 = User.builder()
                .keycloakId("keycloak-id-1")
                .username("user1")
                .email("user1@example.com")
                .build();

        testUser2 = User.builder()
                .keycloakId("keycloak-id-2")
                .username("user2")
                .email("user2@example.com")
                .build();

        testUser1 = userRepository.save(testUser1);
        testUser2 = userRepository.save(testUser2);

        // Créer et sauvegarder les projets UNIQUEMENT avec les compétences autorisées
        project1 = Project.builder()
                .title("Spring Boot Microservices")
                .description("Build microservices with Spring Boot")
                .difficulty(Difficulty.INTERMEDIATE)
                .owner(testUser1)
                .skills(Set.of(Skill.JAVA, Skill.SPRINGBOOT, Skill.ANGULAR)) // DOCKER → ANGULAR
                .build();

        project2 = Project.builder()
                .title("Python Data Analysis")
                .description("Data analysis with Python") // Changé le titre
                .difficulty(Difficulty.BEGINNER)
                .owner(testUser1)
                .skills(Set.of(Skill.PYTHON)) // JAVASCRIPT et REACT → PYTHON
                .build();

        project3 = Project.builder()
                .title("Advanced Python ML")
                .description("Machine Learning with Python") // Changé le titre
                .difficulty(Difficulty.ADVANCED)
                .owner(testUser2)
                .skills(Set.of(Skill.PYTHON, Skill.JAVA)) // TENSORFLOW et DOCKER → JAVA
                .build();

        projectRepository.saveAll(Arrays.asList(project1, project2, project3));
    }

    @Test
    @DisplayName("Test findByOwner - should return all projects for specific user")
    void testFindByOwner() {
        // When
        List<Project> user1Projects = projectRepository.findByOwner(testUser1);
        List<Project> user2Projects = projectRepository.findByOwner(testUser2);

        // Then
        assertThat(user1Projects)
                .hasSize(2)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder(
                        "Spring Boot Microservices",
                        "Python Data Analysis"
                );

        assertThat(user2Projects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Advanced Python ML");
    }

    @Test
    @DisplayName("Test findByDifficulty - should return projects with specific difficulty")
    void testFindByDifficulty() {
        // When
        List<Project> intermediateProjects = projectRepository.findByDifficulty(Difficulty.INTERMEDIATE);
        List<Project> beginnerProjects = projectRepository.findByDifficulty(Difficulty.BEGINNER);
        List<Project> advancedProjects = projectRepository.findByDifficulty(Difficulty.ADVANCED);

        // Then
        assertThat(intermediateProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Spring Boot Microservices");

        assertThat(beginnerProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Python Data Analysis");

        assertThat(advancedProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Advanced Python ML");
    }

    @Test
    @DisplayName("Test findByTitleContainingIgnoreCase - should return projects with matching title")
    void testFindByTitleContainingIgnoreCase() {
        // When
        List<Project> springProjects = projectRepository.findByTitleContainingIgnoreCase("spring");
        List<Project> bootProjects = projectRepository.findByTitleContainingIgnoreCase("BOOT");
        List<Project> pythonProjects = projectRepository.findByTitleContainingIgnoreCase("Python");
        List<Project> advancedProjects = projectRepository.findByTitleContainingIgnoreCase("advanced");
        List<Project> nonExistentProjects = projectRepository.findByTitleContainingIgnoreCase("nonexistent");

        // Then
        assertThat(springProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Spring Boot Microservices");

        assertThat(bootProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Spring Boot Microservices");

        assertThat(pythonProjects)
                .hasSize(2)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder(
                        "Python Data Analysis",
                        "Advanced Python ML"
                );

        assertThat(advancedProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Advanced Python ML");

        assertThat(nonExistentProjects).isEmpty();
    }

    @Test
    @DisplayName("Test findBySkill - should return projects requiring specific skill")
    void testFindBySkill() {
        // When - Testez UNIQUEMENT avec les compétences autorisées
        List<Project> javaProjects = projectRepository.findBySkill(Skill.JAVA);
        List<Project> angularProjects = projectRepository.findBySkill(Skill.ANGULAR);
        List<Project> pythonProjects = projectRepository.findBySkill(Skill.PYTHON);
        List<Project> springbootProjects = projectRepository.findBySkill(Skill.SPRINGBOOT);

        // Then
        assertThat(javaProjects)
                .hasSize(2)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder(
                        "Spring Boot Microservices",
                        "Advanced Python ML"
                );

        assertThat(angularProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Spring Boot Microservices");

        assertThat(pythonProjects)
                .hasSize(2)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder(
                        "Python Data Analysis",
                        "Advanced Python ML"
                );

        assertThat(springbootProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Spring Boot Microservices");
    }

    @Test
    @DisplayName("Test findBySkill - should return projects with multiple skills")
    void testFindBySkill_MultipleSkills() {
        // Given - Créer un projet avec plusieurs compétences AUTORISÉES
        Project multiSkillProject = Project.builder()
                .title("Full Stack Application")
                .description("Full stack app with Java and Angular")
                .difficulty(Difficulty.INTERMEDIATE)
                .owner(testUser1)
                .skills(Set.of(Skill.JAVA, Skill.ANGULAR, Skill.SPRINGBOOT))
                .build();
        projectRepository.save(multiSkillProject);

        // When
        List<Project> javaProjects = projectRepository.findBySkill(Skill.JAVA);
        List<Project> angularProjects = projectRepository.findBySkill(Skill.ANGULAR);

        // Then
        assertThat(javaProjects)
                .hasSize(3)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder(
                        "Spring Boot Microservices",
                        "Advanced Python ML",
                        "Full Stack Application"
                );

        assertThat(angularProjects)
                .hasSize(2)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder(
                        "Spring Boot Microservices",
                        "Full Stack Application"
                );
    }

    @Test
    @DisplayName("Test findById - should return project with owner relationship")
    void testFindById_WithOwner() {
        // When
        Optional<Project> foundProject = projectRepository.findById(project1.getId());

        // Then
        assertThat(foundProject)
                .isPresent()
                .hasValueSatisfying(project -> {
                    assertThat(project.getTitle()).isEqualTo("Spring Boot Microservices");
                    assertThat(project.getOwner()).isNotNull();
                    assertThat(project.getOwner().getId()).isEqualTo(testUser1.getId());
                    assertThat(project.getOwner().getUsername()).isEqualTo("user1");
                });
    }

    @Test
    @DisplayName("Test save - should persist project with user relationship")
    void testSave_WithUser() {
        // Given
        User newUser = User.builder()
                .keycloakId("new-user-keycloak")
                .username("newuser")
                .email("newuser@example.com")
                .build();
        newUser = userRepository.save(newUser);

        Project newProject = Project.builder()
                .title("New Java Project")
                .description("New project with Java")
                .difficulty(Difficulty.BEGINNER)
                .owner(newUser)
                .skills(Set.of(Skill.JAVA))
                .build();

        // When
        Project savedProject = projectRepository.save(newProject);
        projectRepository.flush();

        // Then
        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getOwner()).isEqualTo(newUser);

        Optional<Project> retrievedProject = projectRepository.findById(savedProject.getId());
        assertThat(retrievedProject)
                .isPresent()
                .get()
                .extracting(Project::getOwner)
                .isEqualTo(newUser);
    }

    @Test
    @DisplayName("Test custom query with @Query - should work with Skill enum")
    void testFindBySkill_CustomQuery() {
        // When - Testez avec des compétences AUTORISÉES
        List<Project> angularProjects = projectRepository.findBySkill(Skill.ANGULAR);
        List<Project> springbootProjects = projectRepository.findBySkill(Skill.SPRINGBOOT);

        // Then
        assertThat(angularProjects)
                .hasSize(1)
                .allMatch(project -> project.getSkills().contains(Skill.ANGULAR));

        assertThat(springbootProjects)
                .hasSize(1)
                .allMatch(project -> project.getSkills().contains(Skill.SPRINGBOOT));
    }

    @Test
    @DisplayName("Test all authorized skills")
    void testAllAuthorizedSkills() {
        // Testez que les 4 compétences autorisées fonctionnent
        for (Skill skill : Skill.values()) {
            List<Project> projects = projectRepository.findBySkill(skill);
            // Cela ne devrait pas lever d'exception
            assertThat(projects).isNotNull();
        }
    }

    @Test
    @DisplayName("Test combination of queries - complex business scenario")
    void testComplexBusinessScenario() {
        // Scenario: Find all intermediate or advanced projects for user1 that contain "Boot" or "Python"

        // When
        List<Project> user1Projects = projectRepository.findByOwner(testUser1);

        List<Project> filteredProjects = user1Projects.stream()
                .filter(p -> p.getDifficulty() == Difficulty.INTERMEDIATE ||
                        p.getDifficulty() == Difficulty.ADVANCED)
                .filter(p -> p.getTitle().toLowerCase().contains("boot") ||
                        p.getTitle().toLowerCase().contains("python"))
                .toList();

        // Then
        assertThat(filteredProjects)
                .hasSize(1)
                .extracting(Project::getTitle)
                .containsExactly("Spring Boot Microservices");
    }
}