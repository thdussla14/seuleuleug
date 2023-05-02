package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seuleuleug.domain.HospitalEntityRepository;

@Service
@Slf4j
public class HospitalService {
    @Autowired
    private HospitalEntityRepository hospitalEntityRepository;
}
