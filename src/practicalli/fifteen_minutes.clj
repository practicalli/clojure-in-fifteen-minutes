(ns practicalli.fifteen-minutes)


;; Simple Values - Hello World and Maths

;; str will create a string out of all its arguments

(str "Hello Clojure World," " " "happy " 13 " th birthday in 2020")


;; Math uses function calls rather than operators
;; parentheses () define the order of evaluation
;; parentheses ensure there is no ambiguity in the order of calculation

(+ 1 1) ; => 2
(- 24 4 10) ;; => 10
(* 1 2 (+ 1 2) (* 2 2)) ;; => 24

;; A ratio is a value in Clojure, helping to maintain precision
(/ 22 7) ;; => 22/7

;; A ratio can be coerced into a number type
(/ 22 7.0) ;; => 3.142857142857143

;; Equality is comparing values with the = function
;; assignment is not done with the = function
(= 1 1) ; => true
(= 2 1) ; => false
(odd? 1) ;; => true

;; You need not for logic, too
(not true) ; => false
(not= 1 2 3) ;; => true


;; Collections & Sequences
;;;;;;;;;;;;;;;;;;;

;; A list would be written as just (1 2 3), but we have to quote
;; it to stop the reader thinking it's a function.
;; Also, (list 1 2 3) is the same as '(1 2 3)

;; Both lists and vectors are collections:
(coll? '(1 2 3)) ; => true
(coll? [1 2 3]) ; => true
(coll? {:key "value"}) ;; => true
(coll? #{1 2 3 4}) ;; => true

;; Only lists are seqs.
(seq? '(1 2 3)) ; => true
(seq? [1 2 3]) ; => false
(seq? {:key "value"}) ;; => false
(seq? #{1 2 3 4}) ;; => false


;; Seqs are an interface for logical lists, which can be lazy.
;; "Lazy" means that values are only access in the seq when needed

;; range generates a sequence of numbers
(range 4) ; => (0 1 2 3)

;; (range) ; => (0 1 2 3 4 ...) (an infinite series)
;; without any arguments range creates an infinite sequence,
;; so don't use it alone as it will eat all the computer memory

;; take specifies the number of values wanted from a collection or sequence.
;; range stops once take has all the values it needs
(take 4 (range)) ;  (0 1 2 3)

;; Use cons to add an item to the beginning of a list or vector
(cons 4 [1 2 3]) ; => (4 1 2 3)
(cons 4 '(1 2 3)) ; => (4 1 2 3)

;; Use conj to add an item with respect to the type of collection
;; - a list is a linked list, so values are added at the start
(conj [1 2 3] 4) ; => [1 2 3 4]

;; - a vector is an indexed collection, so random access is fast.
;; values are added to the end of a vector
(conj '(1 2 3) 4) ; => (4 1 2 3)

;; Use concat to add lists or vectors together
(concat [1 2] '(3 4)) ; => (1 2 3 4)

;; Use filter, map to interact with collections
(map inc [1 2 3]) ; => (2 3 4)
(filter even? [1 2 3]) ; => (2)

;; Use reduce to reduce them
(reduce + [1 2 3 4])
;; = (+ (+ (+ 1 2) 3) 4)
;; => 10

;; Reduce can take an initial-value argument too
;; typically used as an accumulator
(reduce conj [] '(3 2 1))
;; = (conj (conj (conj [] 3) 2) 1)
;; => [3 2 1]


;; Function definitions
;;;;;;;;;;;;;;;;;;;;;

;; Use fn to create new functions. A function always returns
;; its last expression.
(fn [] "Hello World")
;; => #function[practicalli.fifteen-minutes/eval7623/fn--7624]

;; The fn function creates an anonymous function definition,
;; which has no external name to be referred by
;; A function definition with fn needs to be called within another expression
((fn [] "Hello World")) ; => "Hello World"

;; Anonymous functions are used with functions iterating over collections
(map (fn [value] (* value 9)) [1 2 3 4 5]) ;; => (9 18 27 36 45)

;; anonymous function has a shorter syntax too
;; dropping the fn and argument syntax
;; using %1 %2 etc to represent arguments
(map #(* %1 9) [1 2 3 4 5]) ;; => (9 18 27 36 45)


;; Give a name to values using the def function
;; names are accessible throughout the namespace
(def clojure-developer-salary "1 million dollars")
;; => #'practicalli.fifteen-minutes/clojure-developer-salary

;; Use the name in other Clojure code
(str "Clojure developers could earn up to " clojure-developer-salary)
;; => "Clojure developers could earn up to 1 million dollars"

;; Define a name for function so you can call it elsewhere in your code
(def hello-world (fn [] "Hello World"))
(hello-world) ; => "Hello World"

;; Using defn is the prefered way to give a function a name
;; It is a macro that is changed to the above code by Clojure when it is read.
(defn hello-world [] "Hello World")

;; The [] is the list of arguments for the function.
;; There can be zero or more arguments
(defn hello [name]
  (str "Hello " name))
(hello "Steve") ; => "Hello Steve"


;; #() is a shorthand for defining a functions,
;; most useful inline
(def hello-terse #(str "Hello " %1))
(hello-terse "Jenny") ;; => "Hello Jenny"

;; You can have multi-variadic functions, useful when you have defaults
(defn hello3
  ([] "Hello World")
  ([name] (str "Hello " name)))

(hello3 "Jake") ;; => "Hello Jake"
(hello3) ;; => "Hello World"

;; Functions can pack extra arguments up in a seq for you
(defn count-args [& args]
  (str "You passed " (count args) " args: " args))
(count-args 1 2 3) ;; => "You passed 3 args: (1 2 3)"

;; You can mix regular and packed arguments
(defn hello-count [name & args]
  (str "Hello " name ", you passed " (count args) " extra args"))

(hello-count "Finn" 1 2 3)
;; => "Hello Finn, you passed 3 extra args"

;; More arguments can be captured using the & and a name
;; In the hello-advanced we capture the mandatory name and address
;; Anything-else is checked to see if its empty and if so, a standard messages is added
;; If anything-else has values, they are added to the string instead.

;; We are using functions from the clojure.string namespace,
;; so we make that namespace accessible using the require function
(require 'clojure.string)

(defn hello-advanced [name address & anything-else]
  (str "Hello " name
       ", I see you live at " address
       (if (nil? anything-else)
         ".  That is all."
         (str "and you also do " (clojure.string/join ", " anything-else)))))
;; => #'clojure-through-code.fifteen-minutes/hello-advanced

(hello-advanced "John Stevenson" "7 Paradise street" )
;; => "Hello John Stevenson, I see you live at 7 Paradise street.  That is all."

(hello-advanced "John Stevenson" "7 Paradise street" "cycling" "swimming")
;; => "Hello John Stevenson, I see you live at 7 Paradise streetand you also do cycling, swimming"



;; Clojure Hashmaps
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; (also referred to as dictionaries in other languages)

;; A Clojure hash-map is a persistent data structure
;; it shared data with copies where relevant
(class {:a 1 :b 2 :c 3}) ;; => clojure.lang.PersistentArrayMap

;; Keywords are pointers to themselves, excellent for lookups
(class :a) ; => clojure.lang.Keyword

;; Maps can use any type as a key, but usually keywords are best
(def string-keys-map (hash-map "a" 1, "b" 2, "c" 3))
string-keys-map  ;; => {"a" 1, "b" 2, "c" 3}

(def keyword-keys-map (hash-map :a 1 :b 2 :c 3))
keyword-keys-map ;; => {:a 1, :c 3, :b 2} (order is not guaranteed)

;; Getting values from maps using keys
(get keyword-keys-map :c)

;; default return value can be set if no key in the collection is found
(get keyword-keys-map :d "Sorry, no data") ;; => "Sorry, no data"

;; Maps can be called just like a function, with a key as the argument
;; This is a short-cut to the get function and useful inside inline functions
(string-keys-map "a") ;; => 1
(keyword-keys-map :a) ;; => 1

;; A Keyword key can also be used as a function that gets its associated value from the map
(:b keyword-keys-map) ;; => 2

;; Retrieving a non-present value returns nil
(string-keys-map "d") ; => nil

;; Use assoc to add new keys to hash-maps
(assoc keyword-keys-map :d 4) ;; => {:a 1, :b 2, :c 3, :d 4}

;; But remember, clojure types are immutable!
keyword-keys-map ;; => {:a 1, :b 2, :c 3}

;; Use dissoc to remove keys
(dissoc keyword-keys-map :a :b) ;; => {:c 3}


;; Sets
;;;;;;

(class #{1 2 3}) ; => clojure.lang.PersistentHashSet

;; Create a set with only a unique set of values
(set [1 2 3 1 2 3 3 2 1 3 2 1]) ;; => #{1 2 3}

;; Add a member with conj
(conj #{1 2 3} 4) ;; => #{1 2 3 4}

;; Remove one with disj
(disj #{1 2 3} 1) ;; => #{2 3}

;; Test for existence by using the set as a function:
(#{1 2 3} 1) ;; => 1
(#{1 2 3} 4) ;; => nil

;; There are more functions in the clojure.sets namespace.

;; Useful forms
;;;;;;;;;;;;;;;;;

;; Logic constructs in clojure are just macros, and look like
;; everything else
(if false "a" "b") ;; => "b"
(if false "a") ;; => nil

;; Use let to create temporary bindings
(let [a 1 b 2]
  (> a b)) ;; => false

;; Group statements together with do
(do
  (print "Hello")
  "World") ;; => "World" (prints "Hello")

;; Functions have an implicit do
(defn print-and-say-hello [name]
  (print "Saying hello to " name)
  (str "Hello " name))
(print-and-say-hello "Jeff") ;;=> "Hello Jeff" (prints "Saying hello to Jeff")

;; So does let
(let [name "Jenny"]
  (print "Saying hello to " name)
  (str "Hello " name)) ; => "Hello Jenny" (prints "Saying hello to Jenny")

;; Libraries
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; require gives access to all the functions from another namespace
(require 'clojure.set)

;; Now we can use set operations with their full name
(clojure.set/intersection #{1 2 3} #{2 3 4}) ;; => #{2 3}
(clojure.set/difference #{1 2 3} #{2 3 4}) ;; => #{1}

;; require can specify an alias for a namespace
;; please use meaningful aliases
(require '[clojure.string :as string] )

;; Use / to call functions from a module
(string/blank? "") ; => true

(string/replace "This is a test." #"[a-o]" string/upper-case) ;; => "THIs Is A tEst."
;; (#"" denotes a regular expression)

;; You can use require in a namespace definition using :require.
;; You don't need to quote your modules if you do it this way.
;; and multiple namespaces can be added using one :require
(ns test
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))



;; Types underlying Clojure
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Types are inferred by Clojure so mostly not specified
;; Interop with the host platform (i.e. Java) may benefit from explicit type coercion

;; Clojure uses Java's object types for booleans, strings and numbers as these are immutable.
;; Use `class` or `type` to inspect them.
(class 1) ; Integer literals are java.lang.Long by default
(class 1.); Float literals are java.lang.Double
(class ""); Strings always double-quoted, and are java.lang.String
(class false) ; Booleans are java.lang.Boolean
(class nil); The "null" value is called nil

;; If you want to create a literal list of data, use ' to make a "symbol"
'(+ 1 2) ; => (+ 1 2)

;; You can eval symbols.
(eval '(+ 1 2)) ; => 3

;; Vectors and Lists are java classes too!
(class [1 2 3]); => clojure.lang.PersistentVector
(class '(1 2 3)); => clojure.lang.PersistentList



;; Java
;;;;;;;;;;;;;;;;;

;; Java has a huge and useful standard library, so
;; you'll want to learn how to get at it.

;; Use import to load a java module
(import java.util.Date)

;; You can import from an ns too.
(ns test
  (:import java.util.Date
           java.util.Calendar))

;; Use the class name with a "." at the end to make a new instance
(Date.) ;; <a date object>

;; Use . to call methods. Or, use the ".method" shortcut
(. (Date.) getTime) ;; <a timestamp>
(.getTime (Date.)) ;; exactly the same thing.

;; Use / to call static methods
(System/currentTimeMillis) ;; <a timestamp> (system is always present)

;; Use doto to make dealing with (mutable) classes more tolerable
(import java.util.Calendar)
(doto (Calendar/getInstance)
  (.set 2000 1 1 0 0 0)
  .getTime) ;; => A Date. set to 2000-01-01 00:00:00
