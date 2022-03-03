package wood.mike.buildersupport.workout

import groovy.transform.ToString

@ToString(includePackage = false)
class Exercise {
    String name
    List<ExerciseSet> exerciseSets = []
}
