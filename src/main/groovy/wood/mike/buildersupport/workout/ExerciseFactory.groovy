package wood.mike.buildersupport.workout

class ExerciseFactory extends AbstractFactory{
    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
            throws InstantiationException, IllegalAccessException {
        Exercise exercise = new Exercise(attributes)
        if( value && value instanceof ExerciseSet ) {
            exercise.exerciseSets.add( value )
        }
        exercise
    }

    void setParent(FactoryBuilderSupport builder,
                   Object parent, Object exercise ) {
        if (parent != null && parent instanceof Workout)
            parent.exercises.add( exercise )
    }

    @Override
    boolean isLeaf() {
        false
    }
}
