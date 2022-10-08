package id.slava.nt.simplenotepad


import org.junit.Test
import com.google.common.truth.Truth.assertThat

private const val TITLE_TEST = "Title"
private const val CONTENT_TEST = "Content"

class MainViewModelTest{


    @Test
    fun `assert if title constant unchanged`(){

        assertThat(TITLE).isEqualTo(TITLE_TEST)

    }

    @Test
    fun `assert if content constant unchanged`(){

        assertThat(CONTENT).isEqualTo(CONTENT_TEST)

    }

}