package br.com.zup.edu.nossozenity.repository;

import br.com.zup.edu.nossozenity.zupper.Zupper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZupperRepository extends JpaRepository<Zupper, Long> {
}
