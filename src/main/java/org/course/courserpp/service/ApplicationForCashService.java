package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationForCashService {

    private final ApplicationForCashRepository applicationForCashRepository;

    public List<ApplicationForCash> findAll() {
        return applicationForCashRepository.findAll();
    }

    public Optional<ApplicationForCash> findById(String id) {
        return applicationForCashRepository.findById(id);
    }

    public ApplicationForCash save(ApplicationForCash application) {
        return applicationForCashRepository.save(application);
    }

    public void delete(String id) {
        applicationForCashRepository.deleteById(id);
    }
}
