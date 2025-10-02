#include <iostream>
#include <stdexcept> // Pour les exceptions

template <typename T>
class vecteur {
private:
    size_t memory_allocated;  // Mémoire allouée
    size_t elements_count;    // Nombre d'éléments dans le vecteur
    T* content;               // Tableau dynamique

    void resize() {
        memory_allocated *= 2; // Double la taille de mémoire
        T* new_content = new T[memory_allocated]; // Nouveau tableau
        for (size_t i = 0; i < elements_count; ++i) {
            new_content[i] = content[i]; // Copie les anciens éléments
        }
        delete[] content; // Supprime l'ancien tableau
        content = new_content; // Le pointeur pointe sur le nouveau tableau
    }

public:
    // Constructeur par défaut
    vecteur() : memory_allocated(4), elements_count(0), content(new T[4]) {}

    // Destructeur
    ~vecteur() {
        delete[] content;
    }

    // Ajouter un élément à la fin
    void push_back(const T& value) {
        if (elements_count == memory_allocated) {
            resize(); // Redimensionner si le tableau est plein
        }
        content[elements_count++] = value;
    }

    // Supprimer le dernier élément
    void pop_back() {
        if (elements_count > 0) {
            --elements_count;
        } else {
            throw std::out_of_range("Le vecteur est vide.");
        }
    }

    // Retourner le dernier élément
    T& back() {
        if (elements_count == 0) {
            throw std::out_of_range("Le vecteur est vide.");
        }
        return content[elements_count - 1];
    }

    // Retourner le premier élément
    T& front() {
        if (elements_count == 0) {
            throw std::out_of_range("Le vecteur est vide.");
        }
        return content[0];
    }

    // Insérer un élément à une position donnée
    void insert(size_t index, const T& value) {
        if (index > elements_count) {
            throw std::out_of_range("Index invalide.");
        }
        if (elements_count == memory_allocated) {
            resize();
        }
        for (size_t i = elements_count; i > index; --i) {
            content[i] = content[i - 1]; // Décalage des éléments
        }
        content[index] = value; // Insertion de l'élément
        ++elements_count;
    }

    // Retourner la taille du vecteur
    size_t size() const {
        return elements_count;
    }

    // Vérifier si le vecteur est vide
    bool empty() const {
        return elements_count == 0;
    }
};

int main() {
    vecteur<int> v;
    v.push_back(10);
    v.push_back(20);
    v.push_back(30);
    v.push_back(40);
    v.push_back(50);
    
    std::cout << "Premier élément : " << v.front() << std::endl;
    std::cout << "Dernier élément : " << v.back() << std::endl;

    v.pop_back();
    std::cout << "Dernier élément après pop : " << v.back() << std::endl;

    v.insert(1, 25);
    std::cout << "Élément à l'index 1 après insertion : " << v.back() << std::endl;

    std::cout << "Taille du vecteur : " << v.size() << std::endl;

    return 0;
}
