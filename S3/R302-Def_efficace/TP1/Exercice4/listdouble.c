#include <stdio.h>
#include <stdlib.h>
#include "listdouble.h"

Cell* createCell(int value) {
    Cell* newCell = (Cell*)malloc(sizeof(Cell));
    newCell->value = value;
    newCell->next = newCell->prev = NULL;
    return newCell;
}

List *listCreate()
{
    List *list = (List *)malloc(sizeof(List));
    list->head = NULL;
    list->size = 0;
    return list;
}

Cell *listFind(List *l, int value)
{
    if (l->head == NULL) {
        return NULL;
    }
    Cell* c = l->head;
    int i = 0;
    while (i <= l->size && c->value != value) {
        c = c->next;
        i++;
    }
    return (i == l->size + 1) ? NULL : c;
}

Cell *listGet(List *l, int index)
{
    if (index < 0 || index >= l->size) {
        return NULL;
    }
    Cell* c = l->head;
    int i = 0;
    if (index < l->size / 2) {
        while (i != index) {
            c = c->next;
            i++;
        }
    } else {
        c = l->head->prev;
        i = l->size - 1;
        while (i != index) {
            c = c->prev;
            i--;
        }
    }
    return c;
}

Cell *listAppend(List *l, int value)
{
    Cell* newCell = createCell(value);
    if (l->size == 0) {
        l->head = newCell;
        newCell->next = newCell->prev = newCell;
    } else {
        newCell->prev = l->head->prev;
        newCell->next = l->head;
        l->head->prev->next = newCell;
        l->head->prev = newCell;
    }
    l->size++;
    return newCell;
}

Cell *listPrepend(List *l, int value)
{
    listAppend(l, value);
    l->head = l->head->prev;
    return l->head;
}

Cell *listInsert(List *l, int value, int index)
{
    if (index >= l->size) {
        index = l->size - 1;
        Cell* c = listGet(l, index);
        Cell* newCell = createCell(value);
        newCell->prev = c;
        newCell->next = l->head;
        c->next = newCell;
        l->head->prev = newCell;
        l->size++;
        return newCell;
    } else if (index < 0) {
        index = 0;
    }
    Cell* newCell = createCell(value);
    if (l->size == 0) {
        l->head = newCell;
        newCell->next = newCell->prev = newCell;
    } else {
        Cell* c = listGet(l, index);
        newCell->prev = c->prev;
        newCell->next = c;
        c->prev->next = newCell;
        c->prev = newCell;
        if (index == 0) {
            l->head = newCell;
        }
    }
    l->size++;
    return newCell;
}

Cell *listReplace(List *l, int value, int index)
{
    if (index < 0 || index >= l->size) {
        return NULL;
    }
    Cell* c = listGet(l, index);
    c->value = value;
    return c;
}

Cell *listRemoveAt(List *l, int index)
{
    if (index < 0 || index >= l->size) {
        return NULL;
    }
    Cell* c;
    if (l->size == 1) {
        c = l->head;
        l->head = NULL;
    } else {
        c = listGet(l, index);
        c->prev->next = c->next;
        c->next->prev = c->prev;
        if (index == 0) {
            l->head = c->next;
        }
    }
    l->size--;
    return c;
}

Cell *listRemove(List *l, int value)
{
    if (l->size < 1) {
        return NULL;
    }
    Cell* c = listFind(l, value);
    if (c == NULL) {
        return NULL;
    }
    if (c == l->head) {
        l->head = c->next;
    }
    c->prev->next = c->next;
    c->next->prev = c->prev;
    l->size--;
    return c;
}

void listPrint(List *l)
{
    if (l->head == NULL) {
        printf("[]\n");
        return;
    }
    Cell* c = l->head;
    printf("[%d", c->value);
    while (c->next != l->head) {
        c = c->next;
        printf(", %d", c->value);
    }
    printf("]\n");
}