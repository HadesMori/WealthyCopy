package com.hadesmori.wealthy.cashflow.data.repository

import com.hadesmori.wealthy.cashflow.data.dao.CashFlowDao
import com.hadesmori.wealthy.cashflow.data.entities.OperationEntity
import com.hadesmori.wealthy.cashflow.data.entities.toDatabase
import com.hadesmori.wealthy.cashflow.domain.model.Operation
import com.hadesmori.wealthy.cashflow.domain.model.OperationType
import com.hadesmori.wealthy.cashflow.domain.model.toDomain
import com.hadesmori.wealthy.cashflow.domain.repository.OperationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationsRepositoryImplementation @Inject constructor(
    private val cashFlowDao: CashFlowDao
) : OperationsRepository {
    override suspend fun addOperation(operation: Operation) {
        cashFlowDao.addOperation(operation.toDatabase())
    }

    override suspend fun getOperationsFromProfile(profileId: Long) : List<Operation>{
        return cashFlowDao.getOperationsFromProfile(profileId).map { it.toDomain() }
    }

    override suspend fun deleteOperation(operationId: Long?) {
        if(operationId != null)
        {
            cashFlowDao.deleteOperation(operationId)
        }
    }

    override suspend fun getBasicStatistics(profileId: Long): BasicStatistics {
        val operations = cashFlowDao.getOperationsFromProfile(profileId).map { it.toDomain() }
        val totalIncome = operations.filter { it.type == OperationType.Income }.sumOf { it.amount.toInt() }
        val totalExpense = operations.filter { it.type == OperationType.Expense }.sumOf { it.amount.toInt() }
        return BasicStatistics(totalIncome, totalExpense)
    }
}

data class BasicStatistics(
    val totalIncome: Int,
    val totalExpense: Int
)