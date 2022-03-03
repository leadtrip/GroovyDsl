package wood.mike.buildersupport.workout

/**
 * Creates a Workout object, could be adapted to generate some sort of Garmin etc workout format to upload to web/watch etc
 * e.g. https://developer.garmin.com/gc-developer-program/training-api/
 *
 * See WorkoutFactoryBuilder for alternative method of achieving same result with cleaner code
 */
class WorkoutBuilder extends BuilderSupport{
    @Override
    protected void setParent(Object parent, Object child) {
        if (child instanceof Warmup && parent instanceof Workout)
            parent.warmup = child
        if (child instanceof Exercise && parent instanceof Workout)
            parent.exercises.add(child)
        if (child instanceof ExerciseSet && parent instanceof Exercise)
            parent.exerciseSets.add(child)
    }

    @Override
    protected Object createNode(Object name) {
        switch (name) {
            case "workout":
                return new Workout()
            case "warmup":
                return new Warmup()
            case "exercise":
                return new Exercise()
            case "exerciseSet":
                return new ExerciseSet()
        }
    }

    @Override
    protected Object createNode(Object name, Object value) {
        Object result = createNode(name)
        if ( value instanceof Workout && result instanceof Warmup ) {
            value.warmup = result
        }
        if ( value instanceof Workout && result instanceof Exercise ) {
            value.exercises.add( result )
        }
        if( value instanceof Exercise && result instanceof ExerciseSet ) {
            value.exerciseSets.add( result )
        }
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        switch (name) {
            case "workout":
                return new Workout(attributes)
            case "warmup":
                return new Warmup(attributes)
            case "exercise":
                return new Exercise(attributes)
            case "exerciseSet":
                return new ExerciseSet(attributes)
        }
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        Object result = createNode(name,attributes)
        if ( value instanceof Workout && result instanceof Warmup ) {
            value.warmup = result
        }
        if ( value instanceof Workout && result instanceof Exercise ) {
            value.exercises.add( result )
        }
        if( value instanceof Exercise && result instanceof ExerciseSet ) {
            value.exerciseSets.add( result )
        }
    }

    @Override
    void nodeCompleted(parent, node) {
        if (node != null)
            println "Node $node completed"
    }
}
