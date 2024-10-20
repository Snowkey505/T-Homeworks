package com.example.t_homework01

interface Animal{
    val weight: Double
    val age: Int
    fun foodRecommendation(): String
    fun toyRecommendation(): String
}

enum class BiteType {
    STRAIGHT,
    OVERBYTE,
    UNDERBYTE
}

enum class BehaviorType {
    ACTIVE,
    PASSIVE
}

interface Dog: Animal{
    val biteType: BiteType
}

interface Cat: Animal{
    val behaviorType: BehaviorType
}

class Husky(override val weight: Double, override val age: Int, override val biteType: BiteType) : Dog {
    override fun foodRecommendation() = "Корм для хаски возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для хаски с типом прикуса: $biteType."
}

class Corgi(override val weight: Double, override val age: Int, override val biteType: BiteType) : Dog {
    override fun foodRecommendation() = "Корм для хаски возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для хаски с типом прикуса: $biteType."
}

class ScottishFold(override val weight: Double, override val age: Int, override val behaviorType: BehaviorType) : Cat {
    override fun foodRecommendation() = "Корм для шотландских кошек возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для шотландских кошек с типом поведения: $behaviorType."
}

class Siamese(override val weight: Double, override val age: Int, override val behaviorType: BehaviorType) : Cat {
    override fun foodRecommendation() = "Корм для сиамских кошек возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для сиамских кошек с типом поведения: $behaviorType."
}

class Zoo {
    fun animalType(breed: String) : String?{
        return when (breed){
            "Husky" -> "Dog"
            "Corgy" -> "Dog"
            "ScottishFold" -> "Cat"
            "Siamese" -> "Cat"
            else -> null
        }
    }
}
