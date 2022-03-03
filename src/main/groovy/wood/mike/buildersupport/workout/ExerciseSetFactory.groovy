package wood.mike.buildersupport.workout

class ExerciseSetFactory extends AbstractFactory{

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
            throws InstantiationException, IllegalAccessException {
        ExerciseSet exerciseSet = new ExerciseSet( attributes )
        if( value && value instanceof Exercise ) {
            value.exerciseSets.add( exerciseSet )
        }
        exerciseSet
    }

    void setParent(FactoryBuilderSupport builder,
                          Object parent, Object exerciseSet ) {
        if (parent != null && parent instanceof Exercise)
            parent.exerciseSets.add( exerciseSet )
    }

    @Override
    boolean isLeaf() {
        true
    }
}
