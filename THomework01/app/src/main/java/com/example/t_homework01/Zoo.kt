package com.example.t_homework01

interface Animal{
    val weight: Double
    val age: Int
    fun foodRecommendation(): String
    fun toyRecommendation(): String
}

enum class BiteType {
    STRAIGHT,
    OVERBYTE
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

class Husky(override val weight: Double, override val age: Int) : Dog {
    override val biteType: BiteType = BiteType.STRAIGHT
    override fun foodRecommendation() = "Корм для хаски возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для хаски с типом прикуса: $biteType."
}

class Corgi(override val weight: Double, override val age: Int) : Dog {
    override val biteType: BiteType = BiteType.OVERBYTE
    override fun foodRecommendation() = "Корм для хаски возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для хаски с типом прикуса: $biteType."
}

class ScottishFold(override val weight: Double, override val age: Int) : Cat {
    override val behaviorType: BehaviorType = BehaviorType.PASSIVE
    override fun foodRecommendation() = "Корм для шотландских кошек возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для шотландских кошек с типом поведения: $behaviorType."
}

class Siamese(override val weight: Double, override val age: Int) : Cat {
    override val behaviorType: BehaviorType = BehaviorType.ACTIVE
    override fun foodRecommendation() = "Корм для сиамских кошек возрастом $age лет и весом $weight кг."
    override fun toyRecommendation() = "Игрушки для сиамских кошек с типом поведения: $behaviorType."
}

class Zoo {
    fun animalType(animal: Animal) : String?{
        if (animal is Dog)
            return "Dog"
        else if (animal is Cat)
            return "Cat"
        return null
    }
}
