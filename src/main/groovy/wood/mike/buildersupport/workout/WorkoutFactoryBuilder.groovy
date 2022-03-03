package wood.mike.buildersupport.workout

/**
 * Rather than extend BuilderSupport where all functionality is contained in the single class and has potential to
 * become unwieldy if the problem domain grows we can split the handling of nodes into separated factory classes with
 * the help of FactoryBuilderSupport as seen here and AbstractFactory
 */
class WorkoutFactoryBuilder extends FactoryBuilderSupport {

    WorkoutFactoryBuilder(boolean init = true) {
        super(init)
    }

    def registerObjectFactories() {
        registerFactory("workout", new WorkoutFactory())
        registerFactory("warmup", new WarmupFactory())
        registerFactory("exercise", new ExerciseFactory())
        registerFactory("exerciseSet", new ExerciseSetFactory())
    }
}
