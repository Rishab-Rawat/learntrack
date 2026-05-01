# Design Notes

## Why ArrayList Instead of Array?

Arrays in Java have a fixed size. When you create `new Student[10]`, you commit to exactly 10 slots — no more, no less. If you add an 11th student you need to manually create a larger array and copy everything over. That is error-prone boilerplate that distracts from the actual problem.

`ArrayList` handles resizing automatically. It grows as you add items and shrinks when you remove them. It also gives you useful methods like `add()`, `remove()`, and iteration with a for-each loop out of the box — none of which arrays provide.

For this project, where students, courses, and enrollments are created at runtime in unpredictable quantities, `ArrayList` is the natural choice. Arrays would only make sense if we knew the exact count in advance and it never changed — which is never true for a management system.

---

## Where Static Members Are Used and Why

Static means *belonging to the class itself*, not to any particular instance. We used static in two places:

**IdGenerator** uses static counters (`studentIdCounter`, `courseIdCounter`, etc.) and static methods (`getNextStudentId()`, etc.). This makes sense because an ID counter is a single global value — there is no reason to have one counter per IdGenerator object. Making the counters static ensures every call to `getNextStudentId()` increments the same counter, regardless of where in the codebase it is called from. The constructor is also private so nobody can accidentally create an IdGenerator object — it is a pure utility class.

**InputValidator** uses static methods for the same reason. Validation logic like "check if a string is empty" or "check if an email contains @" does not depend on any state — it just takes input and returns a result or throws an exception. Making these static means callers can use them directly (`InputValidator.requireNonEmpty(...)`) without creating an object first.

---

## Where Inheritance Is Used and What We Gained

`Person` is the abstract base class. `Student` and `Trainer` both extend it.

The benefit is that the common fields (`id`, `firstName`, `lastName`, `email`) and the `getDisplayName()` method are defined once in `Person`. Without inheritance, we would repeat those four fields and their getters/setters in both `Student` and `Trainer` — any future change (for example, adding a `phoneNumber` field) would need to be made in multiple places.

With inheritance, we add the field once in `Person` and both subclasses get it for free.

We also demonstrated `super()` — each child constructor calls the parent constructor to initialise the shared fields, keeping the initialisation logic in one place.

The `getDisplayName()` override shows polymorphism: `Person` provides a default ("Aarav Sharma"), `Student` overrides it to append batch info ("Aarav Sharma [Batch: 2024-A]"), and `Trainer` overrides it to append specialization. The same method name produces different output depending on the actual type of the object — that is polymorphism in action.

---

## Separation of Concerns

**Entity classes** (`Student`, `Course`, `Enrollment`) only hold data — fields, getters, setters, and a `toString()`.

**Service classes** (`StudentService`, `CourseService`, `EnrollmentService`) contain all business logic — finding records, validating state, managing collections.

**Main.java** handles only UI concerns — printing menus, reading input, calling service methods, and displaying results.

This separation means each class has one clear reason to exist and one reason to change. If the storage mechanism changes (say, we add a database later), only the service layer changes. If the UI changes (say, we add a web front-end), only Main changes. The entity classes remain untouched in both cases.
