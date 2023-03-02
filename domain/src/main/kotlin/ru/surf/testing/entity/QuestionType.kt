package ru.surf.testing.entity

@Suppress("unused")
enum class QuestionType {
    SINGLE_CHOICE {
        override fun validate(answerVariants: Set<AnswerVariant>): String? =
                if (answerVariants.size > 1)
                    "Multiple answers are not allowed for questions with single choice"
                else
                    super.validate(answerVariants)
    },
    MULTIPLE_CHOICE;

    open fun validate(answerVariants: Set<AnswerVariant>): String? =
                if (answerVariants.isEmpty())
                    "No answer provided"
                else
                    null
}