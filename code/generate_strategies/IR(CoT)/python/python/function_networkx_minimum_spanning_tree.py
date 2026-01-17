import networkx as nx

class NetworkXExample:
    def compute_minimum_spanning_tree(self, u: str, v: str, weight: float, x: str = None, y: str = None, weight2: float = None) -> float:
        G = nx.Graph()
        G.add_edge(u, v, weight=weight)

        if x and y and weight2:
            G.add_edge(x, y, weight=weight2)

        mst = nx.minimum_spanning_tree(G)

        total_weight = sum(edge[2]['weight'] for edge in mst.edges(data=True))
        return total_weight
