package tech.gaggle.algo.cleaning

trait Cleaner[I, O] {
  def clean(input: I): O
}
