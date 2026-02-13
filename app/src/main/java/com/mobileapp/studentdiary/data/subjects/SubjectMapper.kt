package com.mobileapp.studentdiary.data.subjects

import com.mobileapp.studentdiary.domain.model.Subject

object SubjectMapper {

    fun fromDomain(domain: Subject): SubjectEntity = SubjectEntity(
        id = domain.id,
        name = domain.name
    )

    fun toDomain(entity: SubjectEntity): Subject = Subject(
        id = entity.id,
        name = entity.name
    )
}
