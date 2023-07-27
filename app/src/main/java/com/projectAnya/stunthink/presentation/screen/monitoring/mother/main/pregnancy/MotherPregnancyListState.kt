package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy

import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy

data class MotherPregnancyListState(
    val isLoading: Boolean = false,
    val pregnancyList: List<Pregnancy> = emptyList(),
    val error: String = ""
)
