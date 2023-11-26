
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navifationview.checkout_database.CheckOutItem

class CheckoutViewModel : ViewModel() {

    private val _checkoutData = MutableLiveData<List<CheckOutItem>?>()
    val checkoutData: MutableLiveData<List<CheckOutItem>?> get() = _checkoutData

    fun deleteCheckoutItem(item: CheckOutItem) {
        // Implement your deletion logic
        val updatedList = _checkoutData.value?.toMutableList()
        updatedList?.remove(item)

        // After deletion, update the LiveData
        _checkoutData.value = updatedList
    }
}


