import networkx as nx

class NetworkXExample:
    def compute_shortest_path(self, start: str, end: str, u: str, v: str, weight: float, x: str = None, y: str = None, weight2: float = None) -> str:
        G = nx.Graph()
        G.add_edge(u, v, weight=weight)

        if x and y and weight2:
            G.add_edge(x, y, weight=weight2)

        try:
            path = nx.dijkstra_path(G, source=start, target=end, weight='weight')
            return " -> ".join(path)
        except nx.NetworkXNoPath:
            return "no path"
