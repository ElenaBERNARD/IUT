#include <stdio.h>
#include <stdlib.h>
#include "listsimple.h"

List *listCreate()
{
    List *l = (List *)malloc(sizeof(List));
    if (l != NULL)
    {
        l->head = NULL;
        l->size = 0;
    }
    return l;
}

Cell *listFind(List *l, int value)
{

    Cell *cell = l->head;
    while ((cell != NULL) && (cell->value != value))
    {
        cell = cell->next;
    }
    return cell;
}

Cell *listGet(List *l, int index)
{
    Cell *cell = l->head;
    if (index < 0 || index >= l->size)
    {
        return NULL;
    }
    for (int i = 0; i < index; i++)
    {
        cell = cell->next;
    }
    return cell;
}

Cell *listAppend(List *l, int value)
{
    Cell *cell = NULL;
    Cell *new = NULL;
    new = (Cell *)malloc(sizeof(Cell));
    new->next = NULL;
    new->value = value;
    if (l->size == 0)
    {
        l->head = new;
    }
    else
    {
        cell = listGet(l, l->size - 1);
        cell->next = new;
    }
    l->size++;
    return new;
}

Cell *listInsert(List *l, int value, int index)
{
    Cell *cell = NULL;
    Cell *new = NULL;
    if (index > l->size)
        index = l->size;
    if (index < 0)
        index = 0;

    new = (Cell *)malloc(sizeof(Cell));
    new->value = value;

    if (l->size == 0 || index == 0)
    {
        new->next = l->head;
        l->head = new;
    }
    else
    {
        cell = listGet(l, index - 1);
        new->next = cell->next;
        cell->next = new;
    }
    l->size++;
    return new;
}

Cell *listReplace(List *l, int value, int index)
{
    Cell *cell = NULL;
    if (index < 0 || index >= l->size)
    {
        return NULL;
    }
    cell = listGet(l, index);
    cell->value = value;
    return cell;
}

Cell *listRemoveAt(List *l, int index)
{

    Cell *cell = NULL;
    Cell *tmp = NULL;
    if (index >= l->size)
    {
        return NULL;
    }
    else if (index == 0)
    {
        tmp = l->head;
        l->head = l->head->next;
    }
    else
    {
        cell = listGet(l, index - 1);
        tmp = cell->next;
        cell->next = tmp->next;
    }
    l->size -= 1;
    return tmp;
}

Cell *listRemove(List *l, int value)
{
    Cell *cell = l->head;
    Cell *prev = NULL;

    while (cell != NULL && cell->value != value)
    {
        prev = cell;
        cell = cell->next;
    }
    if (cell == NULL)
        return NULL;

    if (prev == NULL)
    {
        l->head = cell->next;
    }
    else
    {
        prev->next = cell->next;
    }
    l->size--;
    return cell;
}

void listPrint(List *l)
{
    Cell *cell = l->head;
    while (cell != NULL)
    {
        printf("%d -> ", cell->value);
        cell = cell->next;
    }
    printf("\n");
}

void freeList(List *l)
{
    Cell *cell = l->head;
    Cell *tmp = NULL;
    while (cell != NULL)
    {
        tmp = cell;
        cell = cell->next;
        free(tmp);
    }
    free(l);
}