package wood.mike.buildersupport.workout

class WorkoutFactory extends AbstractFactory{

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
            throws InstantiationException, IllegalAccessException {
        new Workout( attributes )
    }

    @Override
    boolean isLeaf() {
        false
    }

    @Override
    void onNodeCompleted(FactoryBuilderSupport builder,
                                Object parent, Object workout) {
        println workout
    }
}
