package com.hadesmori.wealthy.cashflow.domain.repository

import com.hadesmori.wealthy.cashflow.data.repository.BasicStatistics
import com.hadesmori.wealthy.cashflow.domain.model.Operation

interface OperationsRepository {
    suspend fun addOperation(operation: Operation)
    suspend fun getOperationsFromProfile(profileId: Long) : List<Operation>
    suspend fun deleteOperation(operationId: Long?)
    suspend fun getBasicStatistics(profileId: Long): BasicStatistics
}