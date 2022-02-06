package com.company;

/*
Description:
ДИАЛОГОВОЕ ДЕРЕВО

Речь Мейв представлена в виде диалогового дерева. Где каждый путь это возможный вариант продолжения разговора.
Вам будет дано представление дерева возможных вариантов разговора Мейв.
Ваша задача найти количество всевозможных диалогов, которые состоят из 6 узлов и более.
Корень дерева обозначается 1.
Диалог считается законченным, если при направлении движения от корня дерева, через родительские узлы к дочерним, вы пришли к элементу,
у которого нет дочерних узлов.
Например: на картинке изображено 3 диалога с длиной пути от 6-ти узлов и более.
1-3-5-8-10-12
1-3-5-13-14-15
1-3-5-13-14-16-17

Входные данные:
диалоговое дерево, представленное в виде связи родительских узлов с дочерними, где сначала указан родительский узел, через двоеточие его
дочерние узлы, если узла нет среди родительских, то значит у этого узла нет дочерних элементов

Пример выходных данных:

1:2,3

2:6

3:4,5

4:7

5:8,13

6:9

8:10,11

10:12

13:14

14:15,16

16:17
end         //for breaking while loop

Пример выходных данных: 3
Количество узлов может доходить до 100.
 */

import java.util.*;
import java.util.stream.Collectors;

public class _12DialogTree {

    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String line = "";
        Map<String, List<String>> map = new HashMap<>();

        while (sc.hasNextLine()) {
            line = sc.nextLine();// get the input
            if (line.isEmpty()) continue;
            if (line.equals("end")) break;   //for break from loop
            String parentString = line.substring(0, line.lastIndexOf(':'));
            List<String> children = Arrays.stream(line.substring(line.lastIndexOf(':') + 1).split(",")).collect(Collectors.toList());
            map.put(parentString, children);
        }

        Set<String> leafs = getLeafs(map);

        int result = 0;
        for (String leaf : leafs) { //each leaf is end of lines. We go from end (from leaf) to start (root)
            int length = 1;
            boolean isHasParent = true;

            while (isHasParent) {
                String parent = null;
                for (Map.Entry<String, List<String>> pair : map.entrySet()) {
                    if (pair.getValue().contains(leaf)) {
                        parent = pair.getKey();
                        length++;
                    }
                    if (parent != null) break;
                }
                if (parent != null) {
                    leaf = parent;
                } else {
                    isHasParent = false;
                    if (length >= 6) result++;
                }
            }
        }
        System.out.println(result);
    }

    private static Set<String> getLeafs(Map<String, List<String>> map) {
        Set<String> leafs = new HashSet<>();
        for (Map.Entry<String, List<String>> pair : map.entrySet()) {
            for (String child : pair.getValue()) {       //each leaf is met just once at all child's lists
                boolean isParent = false;
                for (Map.Entry<String, List<String>> mapInside : map.entrySet()) {  //search from each child at childs's lists at the map is it parent for another leafs
                    if (child.equals(mapInside.getKey())) {
                        isParent = true;
                    }
                    if (isParent) {
                        break;
                    }
                }
                if (!isParent) {
                    leafs.add(child);
                }
            }
        }
        return leafs;
    }
}
