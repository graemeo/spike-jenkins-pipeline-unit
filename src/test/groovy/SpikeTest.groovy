import com.lesfurets.jenkins.unit.global.lib.GitSource
import org.assertj.core.api.Assertions

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library

import com.lesfurets.jenkins.unit.BasePipelineTest
import com.lesfurets.jenkins.unit.MethodCall

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

    @Test
    void shouldLoadJenkinsfileWithSharedLibrary() {
        String clonePath = "out"
        def library = library()
                        .name("hello")
                        .retriever(GitSource.gitSource("git@github.com:graemeo/spike-jenkins-pipeline-unit.git"))
                        .targetPath(clonePath)
                        .defaultVersion("master")
                        .allowOverride(true)
                        .implicit(false)
                        .build()

        helper.registerSharedLibrary(library)

        runScript("src/test/jenkins/jenkinsfileWithSharedLibrary")

        Assertions.assertThat(helper.callStack.findAll { call ->
            call.methodName == "sh"
        }.any { call ->
            MethodCall.callArgsToString(call).contains("echo 'goodbye world'")
        }).isTrue()

        assertJobStatusSuccess()
    }

}
