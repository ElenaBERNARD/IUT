import random
import time
import statistics
import matplotlib.pyplot as plt

from ex2 import generate_half_zero_int_tableau

def sequential_search(tab, n):
    for i in range(len(tab)):
        if tab[i] == n:
            return i
    return -1

def binary_search(tab, n):
    left, right = 0, len(tab) - 1
    while left <= right:
        mid = (left + right) // 2
        if tab[mid] == n:
            return mid
        elif tab[mid] < n:
            left = mid + 1
        else:
            right = mid - 1
    return -1

def gen_tab_hide_0_random(n):
    import random
    tab = [random.randint(1, 100) for _ in range(n)]
    pos = random.randint(0, n - 1)
    tab[pos] = 0
    return tab

def gen_tab_hide_0_end(n):
    import random
    tab = [random.randint(1, 100) for _ in range(n - 1)]
    tab.append(0)
    return tab

# Generateur de graphiques (code par ChatGPT)
def time_algorithm(alg, arr, target=0):
    a = arr.copy()
    start = time.perf_counter()
    alg(a, target)
    end = time.perf_counter()
    return (end - start) * 1000.0, a 

def benchmark(generator, alg, sizes, repeats, skip_if_too_slow=False):
    times = []
    for n in sizes:
        sample_times = []
        for _ in range(repeats):
            arr = generator(n)
            t, res = time_algorithm(alg, arr, target=0)
            sample_times.append(t)
            if skip_if_too_slow and t > 5000:
                break
        times.append(statistics.median(sample_times))
    return times

def plot_results(sizes, results, title, filename):
    plt.figure(figsize=(8,5))
    for label, times in results.items():
        plt.plot(sizes, times, marker='o', label=label)
    plt.xlabel('n (array size)')
    plt.ylabel('Median time (ms)')
    plt.title(title)
    plt.xscale('log')
    plt.yscale('linear')
    plt.grid(True, which='both', ls='--', lw=0.5)
    plt.legend()
    plt.tight_layout()
    plt.savefig(filename)
    print(f"Saved plot to {filename}")
    plt.show()

if __name__ == "__main__":
    random.seed(0)
    sizes = [10**i for i in range(3, 7)]  # From 1,000 to 1,000,000
    repeats = 5

    generators = {
        "random_placed_target_search": gen_tab_hide_0_random,
        "end_placed_target_search": gen_tab_hide_0_end,
    }

    algorithms = {
        "sequential_search": sequential_search,
        "dichotomic_search": binary_search,
    }

    for gen_name, gen in generators.items():
        print(f"Benchmarking input: {gen_name}")
        results = {}
        for alg_name, alg in algorithms.items():
            skip_slow = (gen_name == "all_zero" and alg_name == "decalage")
            times = benchmark(gen, alg, sizes, repeats, skip_if_too_slow=skip_slow)
            results[alg_name] = times
            print(f"  {alg_name}: {times}")
        plot_results(sizes, results, f"rise_zero methods on {gen_name}", f"benchmark_{gen_name}.png")
    print("Done.")

