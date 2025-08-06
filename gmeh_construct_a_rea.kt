import kotlin.collections.emptyList

interface AutomationScriptGeneratorApi {
    data class AutomationScript(val id: String, val name: String, val triggers: List<String>, val actions: List<String>)
    data class Trigger(val id: String, val type: String, val params: Map<String, String>)
    data class Action(val id: String, val type: String, val params: Map<String, String>)

    fun generateScript(scriptName: String, triggers: List<Trigger>, actions: List<Action>): AutomationScript
    fun getAvailableTriggers(): List<Trigger>
    fun getAvailableActions(): List<Action>
    fun getScriptById(scriptId: String): AutomationScript?
    fun listAllScripts(): List<AutomationScript>
    fun deleteScript(scriptId: String): Boolean
    fun updateScript(script: AutomationScript): AutomationScript
}

class AutomationScriptGenerator : AutomationScriptGeneratorApi {
    private val scripts: MutableMap<String, AutomationScript> = mutableMapOf()
    private val triggers: MutableList<Trigger> = mutableListOf()
    private val actions: MutableList<Action> = mutableListOf()

    init {
        // load available triggers and actions from database or config file
        triggers.add(Trigger("trigger1", "http_request", mapOf("url" to "https://example.com")))
        triggers.add(Trigger("trigger2", "timer", mapOf("interval" to "5m")))
        actions.add(Action("action1", "send_email", mapOf("to" to "user@example.com", "subject" to "Test email")))
        actions.add(Action("action2", "log_message", mapOf("message" to "Hello, World!")))
    }

    override fun generateScript(scriptName: String, triggers: List<Trigger>, actions: List<Action>): AutomationScript {
        val scriptId = UUID.randomUUID().toString()
        val script = AutomationScript(scriptId, scriptName, triggers.map { it.id }, actions.map { it.id })
        scripts[scriptId] = script
        return script
    }

    override fun getAvailableTriggers(): List<Trigger> = triggers
    override fun getAvailableActions(): List<Action> = actions
    override fun getScriptById(scriptId: String): AutomationScript? = scripts[scriptId]
    override fun listAllScripts(): List<AutomationScript> = scripts.values.toList()
    override fun deleteScript(scriptId: String): Boolean {
        scripts.remove(scriptId)
        return true
    }

    override fun updateScript(script: AutomationScript): AutomationScript {
        scripts[script.id] = script
        return script
    }
}