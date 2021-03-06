import org.apache.maven.model.Model
import org.apache.maven.model.io.ModelReader
import org.codehaus.plexus.PlexusContainer
import org.codehaus.plexus.component.annotations.Component
import org.codehaus.plexus.component.annotations.Requirement
import org.codehaus.plexus.logging.Logger
import org.sonatype.maven.polyglot.io.ModelReaderSupport
import java.io.Reader

@Component(role = ModelReader::class, hint = "kotlin")
class KotlinModelReader : ModelReaderSupport() {
    @Requirement
    private val container: PlexusContainer? = null

    @Requirement
    protected var log: Logger? = null

    private val scriptEngine = KomScriptEngineFactory().getScriptEngine()

    override fun read(komReader: Reader, options: MutableMap<String, *>): Model {
        val project = scriptEngine.eval(komReader)
        return KomConverter.toModel(project as Project)
    }
}