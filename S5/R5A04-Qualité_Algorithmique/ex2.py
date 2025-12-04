import time
import random
import statistics
import matplotlib.pyplot as plt

def generate_random_int_tableau(n):
    return [random.randint(0, 10) for _ in range(n)]

def generate_half_zero_int_tableau(n):
    tableau = [0] * (n // 2) + [random.randint(1, 10) for _ in range(n - n // 2)]
    random.shuffle(tableau)
    return tableau

def generate_all_zero_int_tableau(n):
    return [0] * n

def rise_zero_decalage(tableau):
    end_point = len(tableau) - 1
    i = 0
    while i <= end_point:
        if tableau[i] == 0:
            for j in range(i, end_point):
                tableau[j] = tableau[j + 1]
            tableau[end_point] = 0
            end_point -= 1
        else:
            i += 1

def rise_zero_swap(tableau):
    next_non_zero = 0
    for i in range(len(tableau)):
        if tableau[i] != 0:
            tableau[next_non_zero], tableau[i] = tableau[i], tableau[next_non_zero]
            next_non_zero += 1

def validate_rise_zero(original, modified):
    non_zero_original = [x for x in original if x != 0]
    non_zero_modified = [x for x in modified if x != 0]
    zeros_count_original = original.count(0)
    zeros_count_modified = modified.count(0)
    if any(x == 0 for x in modified[:len(modified) - zeros_count_modified]):
        return False
    return non_zero_original == non_zero_modified and zeros_count_original == zeros_count_modified

def time_algorithm(alg, arr):
    a = arr.copy()
    start = time.perf_counter()
    alg(a)
    end = time.perf_counter()
    return (end - start) * 1000.0, a 

def benchmark(generator, alg, sizes, repeats, validate=True, skip_if_too_slow=False):
    times = []
    for n in sizes:
        sample_times = []
        for _ in range(repeats):
            arr = generator(n)
            t, res = time_algorithm(alg, arr)
            if validate and not validate_rise_zero(arr, res):
                raise RuntimeError(f"Validation failed for {alg.__name__} with n={n}")
            sample_times.append(t)
            if skip_if_too_slow and t > 5000:
                break
        times.append(statistics.median(sample_times))
    return times

# Generateur de graphiques (code par ChatGPT)
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
    sizes = [1000, 5000, 10000, 20000]
    repeats = 5

    generators = {
        "random_zero_rise": generate_random_int_tableau,
        "half_zero_zero_rise": generate_half_zero_int_tableau,
        "all_zero_zero_rise": generate_all_zero_int_tableau,
    }

    algorithms = {
        "decalage": rise_zero_decalage,
        "swap": rise_zero_swap,
    }

    for gen_name, gen in generators.items():
        print(f"Benchmarking input: {gen_name}")
        results = {}
        for alg_name, alg in algorithms.items():
            skip_slow = (gen_name == "all_zero" and alg_name == "decalage")
            times = benchmark(gen, alg, sizes, repeats, validate=True, skip_if_too_slow=skip_slow)
            results[alg_name] = times
            print(f"  {alg_name}: {times}")
        plot_results(sizes, results, f"rise_zero methods on {gen_name}", f"benchmark_{gen_name}.png")
    print("Done.")
