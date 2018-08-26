import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

class SpikeTest extends BasePipelineTest
{

    @Before
    void setUp() {

        super.setUp()
    }

    @Test
    void shouldPrintCallStackAccordinglyWhenLoadingGlobalSharedLibrary() {
        def script = loadScript("vars/hello.groovy")

        script.call()
        printCallStack()
    }

}
