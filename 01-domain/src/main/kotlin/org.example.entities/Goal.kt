package org.example.entities

/**
 * A Prolog composed goal.
 *
 * A [Goal] is composed by one or more [SubGoal]s.
 * It is used to represent goals separated by a comma.
 * Ex: even(X), multipleOf3(X).
 *
 * @property name The unique name of the goal.
 * @property subGoals The [SubGoal]s the goal is composed of.
 */
data class Goal(
    val name: String,
    val subGoals: List<SubGoal>,
)

/**
 * A Prolog sub-goal.
 *
 * @property value The sub-goal Prolog source code.
 */
data class SubGoal(
    val value: Prolog,
)
