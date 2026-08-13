package dio.taskmanager.infra.repository;

import dio.taskmanager.domain.TaskRepository;
import dio.taskmanager.domain.TaskRepositoryTest;

class InMemoryTaskRepositoryImplTest extends TaskRepositoryTest {

    @Override
    protected TaskRepository createRepository() {
        return new InMemoryTaskRepositoryImpl();
    }
}
