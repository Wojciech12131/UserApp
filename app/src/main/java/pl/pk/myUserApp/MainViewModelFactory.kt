package pl.pk.myUserApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.pk.myUserApp.repo.Repository

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}