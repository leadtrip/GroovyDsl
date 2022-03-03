package wood.mike.buildersupport.workout

class WarmupFactory extends AbstractFactory{

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
            throws InstantiationException, IllegalAccessException {
        new Warmup( attributes )
    }

    void setParent(FactoryBuilderSupport builder,
                   Object parent, Object warmup ) {
        if (parent != null && parent instanceof Workout)
            parent.warmup = warmup
    }

    @Override
    boolean isLeaf() {
        false
    }
}
