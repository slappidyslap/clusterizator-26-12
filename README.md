https://visjs.github.io/vis-network/examples/network/events/interactionEvents.html


Разные способы представления графа используются в зависимости от задачи, структуры графа и требований к памяти и времени выполнения. Вот основные способы представления графа:

---

### **1. Матрица смежности (Adjacency Matrix)**

- **Описание:**  
  Используется двумерный массив `n x n`, где `n` — количество вершин. Если существует ребро между вершинами `i` и `j`, то элемент матрицы `matrix[i][j] = 1` (или вес ребра, если граф взвешенный), иначе `0`.

- **Преимущества:**
    - Простое и понятное представление.
    - Быстрый доступ к информации о наличии ребра между двумя вершинами (`O(1)`).

- **Недостатки:**
    - Занимает много памяти (`O(n^2)`), что неэффективно для разреженных графов.
    - Увеличение сложности для операций добавления или удаления рёбер.

- **Пример (ненаправленный граф):**
  ```
  A — B — C
  Матрица:
  A B C
  A 0 1 1
  B 1 0 1
  C 1 1 0
  ```

---

### **2. Список смежности (Adjacency List)**

- **Описание:**  
  Представляет граф в виде массива списков. Каждый элемент массива соответствует вершине и содержит список всех вершин, с которыми она соединена.

- **Преимущества:**
    - Эффективно использует память, особенно для разреженных графов (`O(V + E)`, где `V` — количество вершин, `E` — количество рёбер).
    - Легко добавлять и удалять рёбра.

- **Недостатки:**
    - Проверка существования конкретного ребра может быть медленной (`O(d)`, где `d` — степень вершины).

- **Пример (ненаправленный граф):**
  ```
  A — B — C
  Список:
  A: B, C
  B: A, C
  C: A, B
  ```

---

### **3. Список рёбер (Edge List)**

- **Описание:**  
  Представляет граф в виде списка всех рёбер. Каждое ребро записывается как пара (или тройка для взвешенного графа): `(u, v)` или `(u, v, w)`.

- **Преимущества:**
    - Простое представление.
    - Эффективно для алгоритмов, работающих напрямую с рёбрами (например, алгоритмы Краскала или Беллмана-Форда).

- **Недостатки:**
    - Неудобно проверять наличие рёбер.
    - Затратно строить соседние вершины (`O(E)`).

- **Пример (ненаправленный граф):**
  ```
  A — B — C
  Список:
  (A, B)
  (A, C)
  (B, C)
  ```

---

### **4. Матрица инцидентности (Incidence Matrix)**

- **Описание:**  
  Используется двумерный массив `n x m`, где `n` — количество вершин, `m` — количество рёбер. Если вершина `i` инцидентна ребру `j`, то `matrix[i][j] = 1` (или -1 для направленного графа).

- **Преимущества:**
    - Универсальное представление, подходит для направленных и ненаправленных графов.
    - Полезно в некоторых математических моделях (например, для теории потоков).

- **Недостатки:**
    - Неэффективно для разреженных графов, требует много памяти (`O(V * E)`).
    - Сложно обходить соседние вершины.

- **Пример:**
  ```
  Вершины: A, B, C
  Рёбра: E1(A-B), E2(B-C), E3(A-C)
  Матрица:
      E1  E2  E3
  A   1   0   1
  B   1   1   0
  C   0   1   1
  ```

---

### **5. Словарь смежности (Adjacency Map/Dictionary)**

- **Описание:**  
  Использует хэш-таблицы или словари. Ключ — вершина, значение — список (или множество) соседей.

- **Преимущества:**
    - Эффективное использование памяти для разреженных графов.
    - Быстрый доступ и поиск соседей (в среднем `O(1)` для добавления/удаления рёбер).

- **Недостатки:**
    - Может быть дороже в плане вычислений при использовании вместо массивов.

- **Пример:**
  ```
  A — B — C
  Словарь:
  {
      "A": ["B", "C"],
      "B": ["A", "C"],
      "C": ["A", "B"]
  }
  ```

---

### **6. Объектное представление (Object-Oriented Graph Representation)**

- **Описание:**  
  Граф реализуется как классы. Узлы и рёбра являются объектами, а связи между ними устанавливаются через свойства или ссылки.

- **Преимущества:**
    - Гибкость: можно хранить дополнительные данные (веса, атрибуты узлов/рёбер).
    - Интуитивное представление для сложных графов.

- **Недостатки:**
    - Более сложное управление памятью и доступом к данным.

- **Пример:**
  ```java
  class Node {
      String id;
      List<Node> neighbors;
  }

  class Graph {
      List<Node> nodes;
  }
  ```

---

### **Сравнение подходов**

| Метод               | Память              | Поиск рёбер | Добавление рёбер | Удаление рёбер | Подходит для          |
|---------------------|---------------------|-------------|------------------|----------------|-----------------------|
| Матрица смежности   | O(V²)              | O(1)        | O(1)             | O(1)           | Плотных графов        |
| Список смежности    | O(V + E)           | O(d)        | O(1)             | O(d)           | Разреженных графов    |
| Список рёбер        | O(E)               | O(E)        | O(1)             | O(E)           | Взвешенных графов     |
| Матрица инцидентности| O(V * E)           | O(E)        | O(V)             | O(V)           | Специальных случаев   |
| Словарь смежности   | O(V + E)           | O(1)-O(d)   | O(1)             | O(1)-O(d)      | Разреженных графов    |
| Объектное представление | Зависит от структуры | Зависит от структуры | Зависит от структуры | Зависит от структуры | Гибких реализаций     |

---

### **Выбор подхода**

- **Матрица смежности** — для плотных графов и быстрого доступа к рёбрам.
- **Список смежности** — для разреженных графов и экономии памяти.
- **Список рёбер** — для работы с взвешенными графами (например, алгоритм Краскала).
- **Матрица инцидентности** — для специализированных задач (например, потоки).
- **Словарь смежности** — для удобной и гибкой работы с разреженными графами.
- **Объектное представление** — для сложных графов с дополнительными данными.  