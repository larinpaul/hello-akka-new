package section05playingwithremoteactors

class Section54CreatingASingletonActorInTheCluster {

  // We are going to look at:
  // * Cluster Singleton
  // * How it works
  // * Implementing Singleton Actor

  // Use Cases to Use Singleton
  // * Single point of responsibility for important decisions in the cluster
  // * Single entry point to an external
  // * Single master, many workers
  // * Centralized naming service or routing logic

  // Singleton should not be your first design choice since it has drawbacks...
  // Drawbacks:
  // * Cluster Singleton may be a bottleneck
  // * Cluster Singleton can not be available all the time

}
