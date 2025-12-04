import random
import time
import bisect

import matplotlib.pyplot as plt

def meet_in_middle(nums, K):
    n = len(nums)
    half = n // 2
    A = nums[:half]
    B = nums[half:]

    sumsA = [] 
    for mask in range(1 << len(A)):
        s = 0
        for i in range(len(A)):
            if (mask >> i) & 1:
                s += A[i]
        if s <= K:
            sumsA.append((s, mask))

    sumsB = []
    for mask in range(1 << len(B)):
        s = 0
        for i in range(len(B)):
            if (mask >> i) & 1:
                s += B[i]
        if s <= K:
            sumsB.append((s, mask))

    sumsB.sort(key=lambda x: x[0])
    b_vals = [s for s, m in sumsB]

    best = 0
    best_pair = (0, 0)
    for sA, mA in sumsA:
        rem = K - sA
        i = bisect.bisect_right(b_vals, rem) - 1
        if i >= 0:
            sB, mB = sumsB[i]
            total = sA + sB
            if total > best:
                best = total
                best_pair = (mA, mB)

    chosen = []
    mA, mB = best_pair
    for i in range(len(A)):
        if (mA >> i) & 1:
            chosen.append(i)
    for i in range(len(B)):
        if (mB >> i) & 1:
            chosen.append(half + i)

    return best, chosen

def solve_example():
    c = [2463, 2597, 315, 1089, 759, 599, 1283, 1683, 1625, 2292, 211]
    K = 6790
    best, idx = meet_in_middle(c, K)
    print("Example instance:")
    print("K =", K)
    print("c =", c)
    print("Best sum found =", best)
    print("Chosen elements (indices):", idx)
    print("Chosen elements (values):", [c[i] for i in idx])
    print()

def benchmark(ns, trials=3, rng_seed=0):
    random.seed(rng_seed)
    times_mean = []
    times_std = []
    for n in ns:
        tlist = []
        for t in range(trials):
            # generate random lengths between 100 and 3000
            nums = [random.randint(100, 3000) for _ in range(n)]
            K = int(0.7 * sum(nums))  # K chosen so not everything fits
            t0 = time.perf_counter()
            meet_in_middle(nums, K)
            t1 = time.perf_counter()
            tlist.append(t1 - t0)
        mean = sum(tlist) / len(tlist)
        # simple std
        var = sum((x - mean) ** 2 for x in tlist) / len(tlist)
        std = var ** 0.5
        times_mean.append(mean)
        times_std.append(std)
        print(f"n={n}: mean {mean:.6f}s, std {std:.6f}s over {trials} runs")
    return times_mean, times_std

def plot_results(ns, times_mean, times_std, outname='benchmark_statking_problem_n.png'):
    plt.figure(figsize=(8,5))
    plt.errorbar(ns, times_mean, yerr=times_std, marker='o', capsize=4)
    plt.yscale('linear')
    plt.xlabel('n (number of requests)')
    plt.ylabel('time (s)')
    plt.title('Benchmark: stacking problem (meet-in-the-middle)')
    plt.grid(True, which='both', ls='--', lw=0.5)
    plt.tight_layout()
    plt.savefig(outname, dpi=150)
    print(f"Saved plot to {outname}")

def main():
    # Solve provided example
    solve_example()

    # Benchmark for n = 10,20,30,40
    ns = [10, 20, 30, 40]
    times_mean, times_std = benchmark(ns, trials=5, rng_seed=42)
    plot_results(ns, times_mean, times_std, outname='benchmark_statking_problem_n.png')

if __name__ == "__main__":
    main()