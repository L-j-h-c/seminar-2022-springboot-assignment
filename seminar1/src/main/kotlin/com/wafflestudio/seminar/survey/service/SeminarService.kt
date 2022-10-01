package com.wafflestudio.seminar.survey.service

import com.wafflestudio.seminar.survey.api.Seminar404
import com.wafflestudio.seminar.survey.api.SeminarExceptionType
import com.wafflestudio.seminar.survey.database.OperatingSystemEntity
import com.wafflestudio.seminar.survey.database.OsRepository
import com.wafflestudio.seminar.survey.database.SurveyResponseEntity
import com.wafflestudio.seminar.survey.database.SurveyResponseRepository
import com.wafflestudio.seminar.survey.domain.OperatingSystem
import com.wafflestudio.seminar.survey.domain.SurveyResponse
import org.springframework.stereotype.Service

interface OSService {
    fun osForName(name: String): OperatingSystem
    fun osForId(id: Long): OperatingSystem

}

@Service
class DefaultOSService(
    val repository: OsRepository
): OSService {
    override fun osForName(name: String): OperatingSystem {
        val entity = repository.findByOsName(name) ?: throw Seminar404(SeminarExceptionType.NotExistOSForName)
        return OperatingSystem(entity)
    }

    override fun osForId(id: Long): OperatingSystem {
        val entity = repository.findById(id).orElseThrow() ?: throw Seminar404(SeminarExceptionType.NotExistOSForId)
        return OperatingSystem(entity)
    }

    private fun OperatingSystem(entity: OperatingSystemEntity) = entity.run {
        OperatingSystem(id, osName, price, desc)
    }
}

interface SurveyService {
    fun allSurveyList(): List<SurveyResponse>
    fun surveyForId(id: Long): SurveyResponse
}

@Service
class DefaultSurveyService(
    val repository: SurveyResponseRepository
): SurveyService {
    override fun allSurveyList(): List<SurveyResponse> {
        val entityList = repository.findAll()
        return entityList.map(::SurveyResponse)
    }

    override fun surveyForId(id: Long): SurveyResponse {
        val entity = repository.findById(id).orElseThrow() ?: throw Seminar404(SeminarExceptionType.NotExistSurveyForId)
        return SurveyResponse(entity)
    }

    private fun SurveyResponse(entity: SurveyResponseEntity) = entity.run {
        SurveyResponse(
            id = id,
            operatingSystem = OperatingSystem(operatingSystem),
            springExp = springExp,
            rdbExp = rdbExp,
            programmingExp = programmingExp,
            major = major,
            grade = grade,
            timestamp = timestamp,
            backendReason = backendReason,
            waffleReason = waffleReason,
            somethingToSay = somethingToSay,
        )
    }

    private fun OperatingSystem(entity: OperatingSystemEntity) = entity.run {
        OperatingSystem(id, osName, price, desc)
    }
}