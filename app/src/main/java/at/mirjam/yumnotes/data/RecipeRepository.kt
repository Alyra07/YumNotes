package at.mirjam.yumnotes.data

class RecipeRepository(private val recipeDao: RecipeDao) {
    fun getAllRecipes() = recipeDao.getAllRecipes()

    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}