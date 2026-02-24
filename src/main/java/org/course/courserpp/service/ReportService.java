package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalResponseDto;
import org.course.courserpp.dto.MoneyTransferResponseDto;
import org.course.courserpp.dto.ReplenishmentOperationResponseDto;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final MoneyTransferRepository moneyTransferRepository;
    private final ReplenishmentOperationRepository replenishmentOperationRepository;

    public List<CashWithdrawalResponseDto> getCashWithdrawalsReport() {
        return cashWithdrawalRepository.findAll().stream()
                .map(e -> {
                    CashWithdrawalResponseDto dto = new CashWithdrawalResponseDto();
                    dto.setId(e.getId());
                    dto.setWithdrawalAmount(e.getWithdrawalAmount());
                    dto.setCreatedAt(e.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList());
    }

    public List<MoneyTransferResponseDto> getMoneyTransfersReport() {
        return moneyTransferRepository.findAll().stream()
                .map(e -> {
                    MoneyTransferResponseDto dto = new MoneyTransferResponseDto();
                    dto.setId(e.getId());
                    dto.setSum(e.getSum());
                    dto.setCreatedAt(e.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList());
    }

    public List<ReplenishmentOperationResponseDto> getReplenishmentOperationsReport() {
        return replenishmentOperationRepository.findAll().stream()
                .map(e -> {
                    ReplenishmentOperationResponseDto dto = new ReplenishmentOperationResponseDto();
                    dto.setId(e.getId());
                    dto.setSum(e.getSum());
                    dto.setCreatedAt(e.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList());
    }
}
